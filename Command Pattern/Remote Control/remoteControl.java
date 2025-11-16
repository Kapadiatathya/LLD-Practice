// Command Pattern Example - Remote Control System

interface Command {
    void execute();
    void undo();
}

class Light {
    String location;

    public Light(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " light is ON");
    }

    public void off() {
        System.out.println(location + " light is OFF");
    }
}

class Stereo {
    String location;

    public Stereo(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " stereo is ON");
    }

    public void off() {
        System.out.println(location + " stereo is OFF");
    }

    public void setCD() {
        System.out.println(location + " stereo is set for CD input");
    }

    public void setVolume(int volume) {
        System.out.println(location + " stereo volume set to " + volume);
    }
}

class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }

    public void undo() {
        light.on();
    }
}

class StereoOnWithCDCommand implements Command {
    Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);
    }

    public void undo() {
        stereo.off();
    }
}

class StereoOffCommand implements Command {
    Stereo stereo;

    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    public void execute() {
        stereo.off();
    }

    public void undo() {
        stereo.on();
    }
}

class NoCommand implements Command {
    public void execute() {
    }

    public void undo() {
    }
}

class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPressed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPressed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPressed() {
        System.out.print("Undoing last action: ");
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n------ Remote Control -------\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append("[slot ").append(i).append("] ")
                    .append(onCommands[i].getClass().getSimpleName()).append("    ")
                    .append(offCommands[i].getClass().getSimpleName()).append("\n");
        }
        return sb.toString();
    }
}

// --- Client (Main App) ---
public class remoteControl {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand lightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand lightOff = new LightOffCommand(livingRoomLight);

        StereoOnWithCDCommand stereoOn = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        remote.setCommand(0, lightOn, lightOff);
        remote.setCommand(1, stereoOn, stereoOff);

        System.out.println(remote);

        remote.onButtonWasPressed(0);
        remote.offButtonWasPressed(0);
        remote.undoButtonWasPressed();

        remote.onButtonWasPressed(1);
        remote.offButtonWasPressed(1);
        remote.undoButtonWasPressed();
    }
}
