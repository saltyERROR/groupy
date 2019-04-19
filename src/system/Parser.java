package system;

import java.io.IOException;

public interface Parser extends LanguageApp{
    void parse(String input) throws IOException;
    @Override
    void send();
}
