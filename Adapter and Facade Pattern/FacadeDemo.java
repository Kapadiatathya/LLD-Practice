class Amplifier {
    public void on() { System.out.println("Amp ON"); }
    public void off() { System.out.println("Amp OFF"); }
}

class DVDPlayer {
    public void on() { System.out.println("DVD Player ON"); }
    public void play(String movie) { System.out.println("Playing movie: " + movie); }
    public void off() { System.out.println("DVD Player OFF"); }
}

class Projector {
    public void on() { System.out.println("Projector ON"); }
    public void wideScreenMode() { System.out.println("Projector in widescreen mode"); }
    public void off() { System.out.println("Projector OFF"); }
}

class HomeTheaterFacade {
    private final Amplifier amp;
    private final DVDPlayer dvd;
    private final Projector projector;

    public HomeTheaterFacade(Amplifier amp, DVDPlayer dvd, Projector projector) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        amp.on();
        projector.on();
        projector.wideScreenMode();
        dvd.on();
        dvd.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        amp.off();
        projector.off();
        dvd.off();
    }
}

public class FacadeDemo {
    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade(
                new Amplifier(), new DVDPlayer(), new Projector()
        );

        theater.watchMovie("Avengers: Endgame");
        theater.endMovie();
    }
}
