package Physics;

import java.util.ArrayList;

public class World
{

    Ball ball;
    Function3D function;


    public World(double coefficient_of_friction, double g, double mass, Vector3D v1) {
        function = new Function3D();
        ball = new Ball(g, coefficient_of_friction, function, mass, v1);
        ball.update_forces();
    }

    public ArrayList<Vector3D> hit_ball(double velocity_vector_x, double velocity_vector_y)
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

        ArrayList<Vector3D> v1 = new ArrayList<Vector3D>();

        Vector3D v2 = new Vector3D(ball.get_position().get_x(),ball.get_position().get_y(),ball.get_position().get_z());
        v1.add(v2);
        ball.update_forces();
        ball.update_position(time_step);
//v2.get_x() != ball.get_position().get_x() && v2.get_y() != ball.get_position().get_y() && v2.get_z() != ball.get_position().get_z()
        while (timer < 60)
        {

            System.out.println("position of ball at time = " + timer);
            System.out.println(ball.get_position().get_x());
            System.out.println(ball.get_position().get_y());
            System.out.println(ball.get_position().get_z());

            v1.add(new Vector3D(ball.get_position().get_x(),ball.get_position().get_y(),ball.get_position().get_z()));

            v2.set_x(ball.get_position().get_x());
            v2.set_y(ball.get_position().get_y());
            v2.set_z(ball.get_position().get_z());

            System.out.println();
            ball.update_forces();
            ball.update_position(time_step);


            timer = timer + 1;
        }

        return v1;
    }
}
