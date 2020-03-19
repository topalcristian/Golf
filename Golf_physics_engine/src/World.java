
public class World
{
    private final double g = 9.81;
    private final double coefficient_of_friction = 0.25 ;

    Ball ball;
    Function3D function;

    public World()
    {
        function = new Function3D();
        ball = new Ball(g, coefficient_of_friction, function);
        ball.update_forces();
    }

    public void hit_ball(double velocity_vector_x, double velocity_vector_y)
    {
        ball.hit(velocity_vector_x, velocity_vector_y);
        ball.update_forces();

        System.out.println("position of ball at time = "+0);
        System.out.println(ball.get_position().get_x());
        System.out.println(ball.get_position().get_y());
        System.out.println(ball.get_position().get_z());
        System.out.println();

        double time_step = 1;
        double timer = 1;
        while (timer <= 60)
        {

            ball.update_forces();
            ball.update_position(time_step);

            System.out.println("position of ball at time = "+timer);
            System.out.println(ball.get_position().get_x());
            System.out.println(ball.get_position().get_y());
            System.out.println(ball.get_position().get_z());
            System.out.println();

            timer = timer + 1;
        }
    }
}
