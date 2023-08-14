package TextToImage;

import java.util.List;

public class Body {
        private int width;
        private int height;
        private int steps;
        private int seed;
        private int cfg_scale;
        private int samples;
        private String style_preset;
//        private String clip_guidance_preset;
//        private String sampler;
        private List<Params> text_prompts;

        public Body(int width, int height, int steps, int seed, int cfg_scale, int samples,
                    String style_preset, List<Params> text_prompts)
        {
            this.width = width;
            this.height = height;
            this.steps = steps;
            this.seed = seed;
            this.cfg_scale = cfg_scale;
            this.samples = samples;
            this.style_preset = style_preset;
            this.text_prompts = text_prompts;
        }
}
