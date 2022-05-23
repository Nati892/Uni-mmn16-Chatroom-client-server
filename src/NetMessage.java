import java.io.Serializable;

public class NetMessage implements Serializable {

    private String Text;

    public NetMessage(String s) {
        this.Text = s;
    }

    public String getText() {
        return Text;
    }

}
