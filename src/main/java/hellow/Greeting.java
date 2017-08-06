package hellow;

public class Greeting {

    private final long id;
    private final String content;
    //private final String age;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
        //this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

	

}
