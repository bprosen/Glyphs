package com.renatusnetwork.glyphs.objects.tags;

public class Tag {

    private String name;
    private String title;
    private String creator;

    public Tag(String name, String title, String creator) {
        this.name = name;
        this.title = title;
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
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
        private String creator;

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

        public Builder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public Tag build() {
            return new Tag(name, title, creator);
        }
    }
}
