package com.uco.stlapp.providers

import com.uco.stlapp.models.Article

class ArticleProvider {
    companion object{
        val articleList = listOf<Article>(
            Article("Spiderman", "Marvel", 4, "w", null),
            Article("Daredevil", "Marvel", 3, "q", null),
            Article("Wolverine", "Marvel", 2, "s", null),
            Article("Batman", "DC", 4, "a", null),
            Article("Thor", "Marvel", 2, "s", null),
            Article("Flash", "DC", 5, "f", null),
            Article("Green Lantern", "DC", 1, "a", null),
            Article("Wonder Woman", "DC", 8, "f", null),
            Article("Spiderman", "Marvel", 4, "w", null),
            Article("Daredevil", "Marvel", 3, "q", null),
            Article("Wolverine", "Marvel", 2, "s", null),
            Article("Batman", "DC", 4, "a", null),
            Article("Thor", "Marvel", 2, "s", null),
            Article("Flash", "DC", 5, "f", null),
            Article("Green Lantern", "DC", 1, "a", null),
            Article("Wonder Woman", "DC", 8, "f", null),
        )
    }
}