package com.uco.stlapp.providers

import com.uco.stlapp.models.Article

class ArticleProvider {
    companion object{
        val articleList = listOf<Article>(
            Article("Multímetro", "15XP-A", 4, "Bueno", null),
            Article("Multímetro LCR", "LCR55A", 2, "Bueno", null),
            Article("Puntas de Multímetro", "20A 1000V", 17, "Bueno", null),
            Article("Fuente", "Utp1306", 4, "Regular", null),
            Article("Puntas de Fuente", "10 mA 110V", 2, "Bueno", null),
            Article("Cable de Poder", "110/240V 5A", 21, "Bueno", null),
            Article("Punta de Osciloscopio", "CLEQEE", 4, "Regular", null),
            Article("Router", "1941w-a/k9", 3, "Bueno", null),
            Article("Cable UTP", "Category 5", 16, "Bueno", null),
            Article("Ponchadora", "11212530", 2, "Bueno", null),
            Article("Motor con Reductor", "R130", 27, "Bueno", null),
            Article("Cable de Consola", "DB9 a RJ45", 4, "Bueno", null),
            Article("Pantalla LCD", "LCD1602", 3, "Regular", null),
            Article("Arduino", "UNO", 3, "Bueno", null),
            Article("Protoboard", "MB 10-2", 1, "Malo", null),
            Article("USB B Arduino", "CAB-2716", 3, "Bueno", null),
        )
    }
}