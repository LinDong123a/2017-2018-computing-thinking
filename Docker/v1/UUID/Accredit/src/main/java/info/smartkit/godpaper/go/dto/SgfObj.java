package info.smartkit.godpaper.go.dto;

public class SgfObj {
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String header;
    private String body;

    public SgfObj() {

    }

    public SgfObj(String header, String body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return "SgfObj{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
