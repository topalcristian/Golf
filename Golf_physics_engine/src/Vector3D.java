public class Vector3D
{
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D project_onto(Vector3D other_vector)
    {
        double dot_product = this.dot_product(other_vector);
        double squared_norm = other_vector.get_magnitude()*other_vector.get_magnitude();

        double scalar = dot_product/squared_norm;

        double new_x = scalar*this.get_x();
        double new_y = scalar*this.get_y();
        double new_z = scalar*this.get_z();

        return(new Vector3D(new_x, new_y, new_z));
    }

    public double dot_product(Vector3D other_vector)
    {
        double product_x = this.get_x()*other_vector.get_x();
        double product_y = this.get_y()*other_vector.get_y();
        double product_z = this.get_z()*other_vector.get_z();
        return(product_x+product_y+product_z);
    }

    public double get_magnitude()
    {
        return (Math.sqrt(this.get_x()*this.get_x()+this.get_y()*this.get_y()+this.get_z()*this.get_z()));
    }

    public double get_x()
    {
        return this.x;
    }

    public double get_y()
    {
        return this.y;
    }

    public double get_z()
    {
        return this.z;
    }

    public void set_x(double x)
    {
        this.x = x;
    }

    public void set_y(double y)
    {
        this.y = y;
    }

    public void set_z(double z)
    {
        this.z = z;
    }


}
