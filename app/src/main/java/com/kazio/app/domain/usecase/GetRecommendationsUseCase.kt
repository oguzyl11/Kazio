package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Recommendation
import com.kazio.app.domain.model.RecommendationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase
) {
    operator fun invoke(): Flow<List<Recommendation>> {
        val calendar = Calendar.getInstance()
        val endAt = calendar.timeInMillis
        
        // Go back 7 days
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startAt = calendar.timeInMillis

        return getSummaryUseCase(startAt, endAt).map { summary ->
            val recommendations = mutableListOf<Recommendation>()
            val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))

            // 1. Most profitable platform
            val bestPlatform = summary.platformProfits.maxByOrNull { it.totalIncome }
            if (bestPlatform != null && bestPlatform.totalIncome > 0) {
                recommendations.add(
                    Recommendation(
                        title = "Haftanın Yıldızı: ${bestPlatform.platformName}",
                        description = "Son 7 günde en yüksek geliri (${formatter.format(bestPlatform.totalIncome)}) buradan elde ettiniz.",
                        type = RecommendationType.POSITIVE
                    )
                )
            }

            // 2. High expenses warning
            if (summary.totalIncome > 0) {
                val expenseRatio = (summary.totalExpense / summary.totalIncome) * 100
                if (expenseRatio > 40) {
                    recommendations.add(
                        Recommendation(
                            title = "Giderleriniz Yüksek",
                            description = "Masraflarınız, gelirinizin %${expenseRatio.toInt()}'sini oluşturuyor. Yakıt veya yemek harcamalarını gözden geçirebilirsiniz.",
                            type = RecommendationType.NEGATIVE
                        )
                    )
                } else if (expenseRatio in 1.0..25.0) {
                    recommendations.add(
                        Recommendation(
                            title = "Mükemmel Tasarruf",
                            description = "Giderleriniz sadece %${expenseRatio.toInt()}. Karlılığınız çok iyi durumda!",
                            type = RecommendationType.POSITIVE
                        )
                    )
                }
            } else if (summary.totalExpense > 0) {
                 recommendations.add(
                    Recommendation(
                        title = "Sadece Gider Var",
                        description = "Son 7 günde hiç gelir kaydetmediniz ancak ${formatter.format(summary.totalExpense)} gideriniz var.",
                        type = RecommendationType.NEGATIVE
                    )
                )
            }

            // 3. Low shift/income warning (e.g., if total income is very low over 7 days)
            if (summary.totalIncome in 1.0..500.0) {
                recommendations.add(
                    Recommendation(
                        title = "Düşük Haftalık Kazanç",
                        description = "Bu hafta kazancınız ${formatter.format(summary.totalIncome)} seviyesinde kaldı. Belki birkaç ekstra vardiya alabilirsiniz.",
                        type = RecommendationType.INFO
                    )
                )
            }

            if (recommendations.isEmpty()) {
                recommendations.add(
                    Recommendation(
                        title = "Kayıt Eklemeye Devam Edin",
                        description = "Yeterli kazanç/gider verisi eklendiğinde size özel finansal tavsiyeler burada görünecek.",
                        type = RecommendationType.INFO
                    )
                )
            }

            recommendations
        }
    }
}
