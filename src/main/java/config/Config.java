package config;

import lombok.Getter;

@Getter
public class Config {
    private static Config config;
    public static Config getInstance(){
        if(config == null)
            config = new Config();
        return config;
    }

    private int NUM_CLUSTERS = 3;
}
