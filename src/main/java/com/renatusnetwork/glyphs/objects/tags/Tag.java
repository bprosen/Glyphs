package com.renatusnetwork.glyphs.objects.tags;

import org.bukkit.ChatColor;

public class Tag {

    private String name;
    private String title;
    private String strippedTitle;
    private String creator;
    private int creationDate;

    public Tag(String name, String title, String creator, int creationDate) {
        this.name = name;
        this.title = title;
        this.strippedTitle = ChatColor.stripColor(title);
        this.creator = creator;
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
        this.strippedTitle = ChatColor.stripColor(title);
    }

    public String getTitle() {
        return title;
    }

    public String getStrippedTitle() {
        return strippedTitle;
    }

    public boolean hasTitle() { return title != null; }

    public int getCreationDate() {
        return creationDate;
    }

    public static class Builder {
        private String name;
        private String title;
        private String creator;
        private int creationDate;

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

        public Builder creationDate(int creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Tag build() {
            return new Tag(name, title, creator, creationDate);
        }
    }
}
