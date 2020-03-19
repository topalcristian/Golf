public class Function3D
{

    private static final double approximate_limit = 0.0000001;

    public Function3D()
    {
    }

    public double apply(double x, double y)
    {
        return (1);
    }


    public double derivative_by_x(double x, double y)
    {
        return ((this.apply(x+approximate_limit, y)-this.apply(x,y))/approximate_limit);
    }

    public double derivative_by_y(double x, double y)
    {
        return ((this.apply(x, y+approximate_limit)-this.apply(x,y))/approximate_limit);
    }

}
