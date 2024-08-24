package com.renatusnetwork.glyphs.objects.tags;

import java.util.UUID;

public class Tag {

    private String name;
    private String title;
    private String creatorName;

    public Tag(String name, String title, String creatorName) {
        this.name = name;
        this.title = title;
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean equals(Tag other) {
        return other != null && name.equals(other.name);
    }

    public static class Builder {
        private String name;
        private String title;
        private String creatorName;

        public static Builder create() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder creatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public Tag build() {
            return new Tag(name, title, creatorName);
        }
    }
}
