import java.util.ArrayList;

public class Ball {
    private double mass;
    private Vector3D position;

    private Vector3D velocity_vector;
    private Vector3D acceleration_vector;

    private double g;
    private double coefficient_of_friction;
    private Function3D function;

    private Force gravitational_force;
    private Force friction_force;

    public Ball(double g, double coefficient_of_friction, Function3D f)
    {
        this.mass = 45.93; //in grams
        this.position = new Vector3D(0,0,f.apply(0,0));

        velocity_vector = new Vector3D(0,0,f.apply(0,0));
        acceleration_vector = new Vector3D(0,0,f.apply(0,0));

        this.g = g;
        this.coefficient_of_friction = coefficient_of_friction;
        this.function = f;
    }

    public Vector3D get_position()
    {
        return this.position;
    }

    public void update_forces()
    {
        //update gravitational force

        double derivative_by_x = this.function.derivative_by_x(this.get_position().get_x(), this.get_position().get_y());
        double derivative_by_y = this.function.derivative_by_y(this.get_position().get_x(), this.get_position().get_y());

        double g_x = -1*this.mass*this.g*derivative_by_x;
        double g_y = -1*this.mass*this.g*derivative_by_y;
        double g_z = this.function.apply(derivative_by_x, derivative_by_y);

        this.gravitational_force = new Force(g_x, g_y, g_z);

        //update friction

        double scalar = -1*this.coefficient_of_friction*this.g*this.mass*(1/this.velocity_vector.get_magnitude());

        this.friction_force = new Force(
                scalar*this.velocity_vector.get_x(),
                scalar*this.velocity_vector.get_y(),
                scalar*this.velocity_vector.get_z());
    }

    public void update_position(double time_step)
    {
        /**Euler*/

        double sum_of_forces_x = this.gravitational_force.get_x()+this.friction_force.get_x();
        double sum_of_forces_y = this.gravitational_force.get_y()+this.friction_force.get_y();
        double sum_of_forces_z = this.gravitational_force.get_z()+this.friction_force.get_z();

        this.acceleration_vector.set_x(sum_of_forces_x*(1/this.mass));
        this.acceleration_vector.set_y(sum_of_forces_y*(1/this.mass));
        this.acceleration_vector.set_z(function.apply(this.acceleration_vector.get_x(), this.acceleration_vector.get_y()));

        double updated_position_x = this.position.get_x()+time_step*this.velocity_vector.get_x();
        double updated_position_y = this.position.get_y()+time_step*this.velocity_vector.get_y();
        double updated_position_z = function.apply(updated_position_x, updated_position_y);

        this.position.set_x(updated_position_x);
        this.position.set_y(updated_position_y);
        this.position.set_z(updated_position_z);

        double updated_velocity_x = this.velocity_vector.get_x()+time_step*this.acceleration_vector.get_x();
        double updated_velocity_y = this.velocity_vector.get_y()+time_step*this.acceleration_vector.get_y();
        double updated_velocity_z = function.apply(updated_velocity_x, updated_velocity_y);

        this.velocity_vector.set_x(updated_velocity_x);
        this.velocity_vector.set_y(updated_velocity_y);
        this.velocity_vector.set_z(updated_velocity_z);


    }

    public void hit(double new_velocity_vector_x, double new_velocity_vector_y)
    {
        this.velocity_vector.set_x(new_velocity_vector_x);
        this.velocity_vector.set_y(new_velocity_vector_y);
        this.velocity_vector.set_z(function.apply(new_velocity_vector_x, new_velocity_vector_y));
    }

    public Vector3D get_friction_force()
    {
        return this.friction_force;
    }
    public Vector3D get_velocity_vector()
    {
        return this.velocity_vector;
    }
    public Vector3D get_acceleration_vector()
    {
        return this.acceleration_vector;
    }
    public Force get_gravitational_force()
    {
        return this.gravitational_force;
    }

}