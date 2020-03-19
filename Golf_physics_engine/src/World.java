
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

        System.out.println(this.ball.get_gravitational_force().get_magnitude());

        double time_step = 1;
        double timer = 1;
        while (timer <= 60)
        {

            ball.update_forces();
            ball.update_position(time_step);

            System.out.println(this.ball.get_gravitational_force().get_magnitude());

            timer = timer + 1;
        }
    }
}
