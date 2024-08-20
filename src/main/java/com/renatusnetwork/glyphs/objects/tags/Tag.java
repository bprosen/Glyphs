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

    public static class Builder {
        private UUID id;
        private String name;
        private String display;
        private String creatorName;

        public static Builder create() {
            return new Builder();
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Builder setCreatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public Tag build() {
            return new Tag(id, name, display, creatorName);
        }
    }
}
