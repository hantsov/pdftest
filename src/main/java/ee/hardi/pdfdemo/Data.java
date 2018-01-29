package ee.hardi.pdfdemo;


import java.time.LocalDateTime;

public class Data {

    private String name = "Test Nimi";
    private LocalDateTime time = LocalDateTime.now();

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
