package ImageToImage;

import java.util.List;

public class BodyToJson {
    private byte[] init_image;
    private String init_image_mode;
    private double image_strength;
    private int samples;
    private int steps;
    private int seed;
    private int cfg_scale;
    private String style_preset;

    private List<ParamsToJSON> text_prompts;

    public BodyToJson(byte[] init_image, String init_image_mode, double image_strength, int samples, int steps,
                      int seed, int cfg_scale, String style_preset, List<ParamsToJSON> text_prompts) {
        this.init_image = init_image;
        this.init_image_mode = init_image_mode;
        this.image_strength = image_strength;
        this.samples = samples;
        this.steps = steps;
        this.seed = seed;
        this.cfg_scale = cfg_scale;
        this.style_preset = style_preset;
        this.text_prompts = text_prompts;
    }
}
