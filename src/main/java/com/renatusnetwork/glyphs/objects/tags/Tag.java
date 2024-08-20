package com.renatusnetwork.glyphs.objects.tags;

import java.util.UUID;

public class Tag {

    private UUID id;
    private String name;
    private String display;
    private String creatorName;

    public Tag(UUID id, String name, String display, String creatorName) {
        this.id = id;
        this.name = name;
        this.display = display;
        this.creatorName = creatorName;
    }

    public String getDisplay() {
        return display;
    }

    public boolean equals(Tag other) {
        return other != null && id.equals(other.id);
    }

    public static class TagBuilder {
        private UUID id;
        private String name;
        private String display;
        private String creatorName;

        public TagBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public TagBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TagBuilder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public TagBuilder setCreatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public Tag build() {
            return new Tag(id, name, display, creatorName);
        }
    }
}
