package com.uco.stlapp.providers

import com.uco.stlapp.models.Article

class ArticleProvider {
    companion object{
        val articleList = listOf<Article>(
            Article("Spiderman", "Marvel", 4, "w", ""),
            Article("Daredevil", "Marvel", 3, "q", ""),
            Article("Wolverine", "Marvel", 2, "s", ""),
            Article("Batman", "DC", 4, "a", ""),
            Article("Thor", "Marvel", 2, "s", ""),
            Article("Flash", "DC", 5, "f", ""),
            Article("Green Lantern", "DC", 1, "a", ""),
            Article("Wonder Woman", "DC", 8, "f", ""),
            Article("Spiderman", "Marvel", 4, "w", ""),
            Article("Daredevil", "Marvel", 3, "q", ""),
            Article("Wolverine", "Marvel", 2, "s", ""),
            Article("Batman", "DC", 4, "a", ""),
            Article("Thor", "Marvel", 2, "s", ""),
            Article("Flash", "DC", 5, "f", ""),
            Article("Green Lantern", "DC", 1, "a", ""),
            Article("Wonder Woman", "DC", 8, "f", ""),
        )
    }
}