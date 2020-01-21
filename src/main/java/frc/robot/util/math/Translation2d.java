package frc.robot.util.math;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A translation in a 2d coordinate frame. Translations are simply shifts in an
 * (x, y) plane.
 */
public class Translation2d extends GZGeometry<Translation2d> implements ITranslation2d<Translation2d> {
    public static final Translation2d kIdentity = new Translation2d();
    private double x_;
    private double y_;

    public static Translation2d identity() {
        return kIdentity;
    }

    public Translation2d() {
        x_ = 0;
        y_ = 0;
    }

    public Translation2d(double x, double y) {
        x_ = x;
        y_ = y;
    }

    public Translation2d(final Translation2d other) {
        x_ = other.x_;
        y_ = other.y_;
    }

    public Translation2d(final Translation2d start, final Translation2d end) {
        x_ = end.x_ - start.x_;
        y_ = end.y_ - start.y_;
    }

    public static Translation2d fromPolar(Rotation2d direction, double magnitude) {
        return new Translation2d(direction.cos() * magnitude, direction.sin() * magnitude);
    }

    private static Translation2d rotateAround(final Translation2d point, final Translation2d rotateAround,
                                              final Rotation2d rotation) {
        Translation2d temp = point.translateBy(rotateAround.inverse());
        temp = temp.rotateBy(rotation);
        temp = temp.translateBy(rotateAround);
        return temp;
    }

    private static boolean insideRange(Translation2d point, Translation2d bottomLeft, Translation2d topRight) {
        boolean inside = true;

        inside &= point.x() < topRight.x() && point.x() > bottomLeft.x();
        inside &= point.y() < topRight.y() && point.y() > bottomLeft.y();

        return inside;
    }

    public Translation2d nearest(List<Translation2d> translations) {
        return nearest(translations, Double.POSITIVE_INFINITY);
    }

    /**
     * The "norm" of a transform is the Euclidean distance in x and y.
     *
     * @return sqrt(x ^ 2 + y ^ 2)
     */
    public double norm() {
        return Math.hypot(x_, y_);
    }

    public double norm2() {
        return x_ * x_ + y_ * y_;
    }

    private static double dot(final Translation2d a, final Translation2d b) {
        return a.x_ * b.x_ + a.y_ * b.y_;
    }

    public double x() {
        return x_;
    }

    public double y() {
        return y_;
    }

    /**
     * We can compose Translation2d's by adding together the x and y shifts.
     *
     * @param other The other translation to add.
     * @return The combined effect of translating by this object and the other.
     */
    public Translation2d translateBy(final Translation2d other) {
        return new Translation2d(x_ + other.x_, y_ + other.y_);
    }

    public static ArrayList<Translation2d> rotateListBy(List<Translation2d> list, Rotation2d rotation) {
        var newList = getEmptyList();

        for (Translation2d l : list) {
            newList.add(l.rotateBy(rotation));
        }

        return newList;
    }

    public static ArrayList<Rotation2d> getListToRotation(List<Translation2d> list) {
        ArrayList<Rotation2d> array = new ArrayList<>();

        for (Translation2d t : list)
            array.add(t.direction());

        return array;
    }

    public boolean atOrigin() {
        return distance(kIdentity) < GZUtil.kEpsilon;
    }

    public Translation2d setNorm(double newNorm) {
        Translation2d t = new Translation2d(this);
        t = t.scale(1 / t.norm());
        t = t.scale(newNorm);
        return t;
    }

    /**
     * We can also rotate Translation2d's. See:
     * https://en.wikipedia.org/wiki/Rotation_matrix
     *
     * @param rotation The rotation to apply.
     * @return This translation rotated by rotation.
     */
    public Translation2d rotateBy(final Rotation2d rotation) {
        return new Translation2d(x_ * rotation.cos() - y_ * rotation.sin(), x_ * rotation.sin() + y_ * rotation.cos());
    }

    public boolean insideRange(Translation2d bottomLeft, Translation2d topRight) {
        return insideRange(this, bottomLeft, topRight);
    }

    public static Rotation2d funkyGetAngle(final Translation2d a, final Translation2d b) {
        double cos_angle = dot(a, b) / (a.norm() * b.norm());
        if (Double.isNaN(cos_angle)) {
            return new Rotation2d();
        }
        return Rotation2d.fromRadians(Math.acos(Math.min(1.0, Math.max(cos_angle, -1.0))));
    }

    public Translation2d rotateAround(final Pose2d pose) {
        return rotateAround(pose.getTranslation(), pose.getRotation());
    }

    public static Rotation2d getAngle(final Translation2d a, final Translation2d b) {
        Translation2d angle = a.inverse().translateBy(b);
        var ret = angle.direction();
        return ret;
    }

    public Translation2d rotateAround(final Translation2d rotateAround, final Rotation2d rotation) {
        return rotateAround(this, rotateAround, rotation);
    }

    public static double cross(final Translation2d a, final Translation2d b) {
        return a.x_ * b.y_ - a.y_ * b.x_;
    }

    public Rotation2d direction() {
        return new Rotation2d(x_, y_, true);
    }

    /**
     * The inverse simply means a Translation2d that "undoes" this object.
     *
     * @return Translation by -x and -y.
     */
    public Translation2d inverse() {
        return new Translation2d(-x_, -y_);
    }

    @Override
    public Translation2d interpolate(final Translation2d other, double x) {
        if (x <= 0) {
            return new Translation2d(this);
        } else if (x >= 1) {
            return new Translation2d(other);
        }
        return extrapolate(other, x);
    }

    private int nearestIndex(List<Translation2d> translations, double maxDistance) {
        double minDistance = Double.POSITIVE_INFINITY;
        int minDistanceIndex = -1;
        for (int i = 0; i < translations.size(); i++) {
            Translation2d t = translations.get(i);
            double distance = t.distance(this);

            if (distance > maxDistance) {
                distance = Double.POSITIVE_INFINITY;
            }

            if (distance < minDistance) {
                minDistance = distance;
                minDistanceIndex = i;
            }
        }

        return minDistanceIndex;
    }

    public static ArrayList<Translation2d> getEmptyList() {
        return new ArrayList<Translation2d>();
    }

    //https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
    private static boolean onSegment(Translation2d p, Translation2d q, Translation2d r) {
        return q.x() <= Math.max(p.x(), r.x()) && q.x() >= Math.min(p.x(), r.x()) &&
                q.y() <= Math.max(p.y(), r.y()) && q.y() >= Math.min(p.y(), r.y());
    }

    //https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
    private static int orientation(Translation2d p, Translation2d q, Translation2d r) {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (int) ((q.y() - p.y()) * (r.x() - q.x()) -
                (q.x() - p.x()) * (r.y() - q.y()));

        if (val == 0) return 0; // colinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    private Translation2d extrapolate(final Translation2d other, double x) {
        return new Translation2d(x * (other.x_ - x_) + x_, x * (other.y_ - y_) + y_);
    }

    @Override
    public String toCSV() {
        final DecimalFormat fmt = new DecimalFormat("#0.0000");
        return fmt.format(x_) + "," + fmt.format(y_);
//        return x() + "," + y();
    }

    private String toString(boolean angle) {
        final DecimalFormat fmt = new DecimalFormat("#0.000");
        String out = "(" + fmt.format(x_) + "," + fmt.format(y_) + ")";
        if (angle) {
            out += this.direction().toString();
        }
        return out;
    }

    /**
     * Normalizing a vector scales it so that its norm is 1 while maintaining its
     * direction. If input is a zero vector, return a zero vector.
     *
     * @return r / norm(r) or (0,0)
     */
    private Translation2d normalize() {
        if (epsilonEquals(identity(), GZUtil.kEpsilon))
            return this;
        return scale(1.0 / norm());
    }

    /**
     * The scalar projection of a vector u onto a vector v is the length of the
     * "shadow" cast by u onto v under a "light" that is placed on a line normal to
     * v and containing the endpoint of u, given that u and v share a starting
     * point. tl;dr: _* u /| / | / | v *---+---------------->* \___/ | scal_v(u)
     * u.scal(v)
     *
     * @return (u . v) / norm(v)
     */
    private double scal(Translation2d v) {
        return dot(this, v) / v.norm();
    }

    public int quadrant() {
        return direction().quadrant();
        /*if (x() > 0 && y() > 0)
            return 1;
        else if (x() < 0 && y() > 0)
            return 2;
        else if (x() < 0 && y() < 0)
            return 3;
        else if (x() > 0 && y() < 0)
            return 4;

        return 0;*/
    }

    /**
     * The projection of a vector u onto a vector v is the vector in the direction
     * of v with the magnitude u.scal(v).
     *
     * @return u.scal(v) * v / norm(v)
     */
    public Translation2d proj(Translation2d v) {
        return v.normalize().scale(scal(v));
    }

    public boolean isWithinAngle(Translation2d A, Translation2d B, Translation2d C) {
        return isWithinAngle(A, B, C, false);
    }

    /**
     * https://stackoverflow.com/a/1167047/6627273 A point D is considered "within"
     * an angle ABC when cos(DBM) > cos(ABM) where M is the midpoint of AC, so ABM
     * is half the angle ABC. The cosine of an angle can be computed as the dot
     * product of two normalized vectors in the directions of its sides. Note that
     * this definition of "within" does not include points that lie on the sides of
     * the given angle. If `vertical` is true, then check not within the given
     * angle, but within the image of that angle rotated by pi about its vertex.
     *
     * @param A        A A point on one side of the angle.
     * @param B        B The vertex of the angle.
     * @param C        C A point on the other side of the angle.
     * @param vertical vertical Whether to check in the angle vertical to the
     *                 one given
     * @return Whether this translation is within the given angle.
     * @author Joseph Reed
     */
    private boolean isWithinAngle(Translation2d A, Translation2d B, Translation2d C, boolean vertical) {
        Translation2d M = A.interpolate(C, 0.5); // midpoint
        Translation2d m = (new Translation2d(B, M)).normalize(); // mid-vector
        Translation2d a = (new Translation2d(B, A)).normalize(); // side vector
        Translation2d d = (new Translation2d(B, this)).normalize(); // vector to here
        if (vertical) {
            m = m.inverse();
            a = a.inverse();
        }
        return Translation2d.dot(d, m) > Translation2d.dot(a, m);
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    //https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
    public static boolean intersects(Translation2d p1, Translation2d q1, Translation2d p2, Translation2d q2) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);// Doesn't fall in any of the above cases
    }

    /**
     * Assumes an angle centered at the origin.
     */
    private boolean isWithinAngle(Translation2d A, Translation2d C, boolean vertical) {
        return isWithinAngle(A, identity(), C, vertical);
    }

    public Translation2d translateBy(double distance, Rotation2d angle) {
        return this.translateBy(Translation2d.fromPolar(angle, distance));
    }

    public Translation2d translateBy(double distance, double angle) {
        return translateBy(distance, new Rotation2d(angle));
    }

    private Translation2d nearest(List<Translation2d> translations, double maxDistance) {
        int index = nearestIndex(translations, maxDistance);
        if (index == -1)
            return null;
        return translations.get(index);
    }

    public Rotation2d getAngle(final Translation2d other) {
        return getAngle(this, other);
    }

    public Translation2d rotateAroundOrigin(final Rotation2d rotation) {
        return rotateAround(new Translation2d(), rotation);
    }

    /**
     * The distance between a point and a line can be computed as a scalar
     * projection.
     *
     * @param a One point on the line.
     * @param b Another point on the line.
     */
    public double distanceToLine(Translation2d a, Translation2d b) {
        Translation2d point = new Translation2d(a, this);
        Translation2d line = new Translation2d(a, b);
        Translation2d perpLine = line.rotateBy(new Rotation2d(90));
        return Math.abs(point.scal(perpLine));

        // let's use readable code from now on, not golfed one-liners, shall we?
        // return Math.abs((new Translation2d(a,this))scal((new
        // Translation2d(a,b)).rotateBy(new Rotation2d(90))));
    }

    public Translation2d scale(double s) {
        return new Translation2d(x_ * s, y_ * s);
    }

    public boolean equals(Translation2d other, double epsilon) {
        return distance(other) < epsilon;
    }

    public boolean epsilonEquals(final Translation2d other, double epsilon) {
        return GZUtil.epsilonEquals(x(), other.x(), epsilon) && GZUtil.epsilonEquals(y(), other.y(), epsilon);
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public Translation2d getFlipped() {
        return new Translation2d(y_, x_);
    }

    public Translation2d getNegatedY() {
        return new Translation2d(x(), -y());
    }

    public Translation2d getNegatedX() {
        return new Translation2d(-x(), y());
    }

    public Translation2d mirror() {
        double newY;

        final double centerLine = 27.0 * 6.0;

        newY = (centerLine + (centerLine - y()));

        return new Translation2d(x(), newY);
    }

    public boolean isWithinAngle(Translation2d A, Translation2d C) {
        return isWithinAngle(A, C, false);
    }

    @Override
    public double distance(final Translation2d other) {
        return inverse().translateBy(other).norm();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof Translation2d))
            return false;
        return distance((Translation2d) other) < GZUtil.kEpsilon;
    }

    @Override
    public Translation2d getTranslation() {
        return this;
    }
}