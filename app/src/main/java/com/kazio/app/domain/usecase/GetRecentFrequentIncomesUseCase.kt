package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.FrequentIncome
import com.kazio.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentFrequentIncomesUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) {
    operator fun invoke(): Flow<List<FrequentIncome>> {
        return incomeRepository.getAllIncomes().map { incomes ->
            incomes.sortedByDescending { it.occurredAt }
                .take(50) // look at the last 50 entries
                .groupBy { FrequentIncome(it.platformId, it.amount, it.note) }
                .map { (key, list) -> key to list.size }
                .sortedByDescending { it.second } // sort by frequency
                .map { it.first }
                .take(3) // Top 3 most frequent
        }
    }
}
