package org.usfirst.frc.team6637.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Right_Auton_CommandGroup extends CommandGroup {

	public Right_Auton_CommandGroup() {
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {

			// SWITCH IS OURS
			if (gameData.charAt(0) == 'R') {

				// raise elevator
				addParallel(new Elevator_Auton_Raise_Command(25000));

				// drive forward 14 feet - 168 inches
				addSequential(new Drive_GoStraight_Command(130.0, 0.75, 24.0));

				// turn left
				addSequential(new Drive_TurnToAngle_Command(-90.0));

				// drive forward 2 feet
				addSequential(new Drive_GoStraight_Command(32.0, 0.6, 11.0));

				// drop cube
				addSequential(new Gripper_Auton_Open_Command());

				// SCALE IS OURS
			} else if (gameData.charAt(1) == 'R') {

				// drive forward 27 feet - 324 inches
				addSequential(new Drive_GoStraight_Command(204.0, 0.75, 24.0));

				// raise elevator
				addSequential(new Elevator_Auton_Raise_Command(55300));

				// turn left
				addSequential(new Drive_TurnToAngle_Command(-90.0));

				// drive forward 1 foot
				addSequential(new Drive_GoStraight_Command(28.0, 0.35, 11.0));

				// drop cube
				addSequential(new Gripper_Auton_Open_Command());

				// NEITHER IS OURS - Go through the alley
			} else {

				// raise elevator
				addSequential(new Elevator_Auton_Raise_Command(10000));

				// drive forward 20.5 feet - 246 inches
				addSequential(new Drive_GoStraight_Command(228.0, 0.6, 24.0));

				// turn left
				addSequential(new Drive_TurnToAngle_Command(-90.0));

			}
		}
	}
}