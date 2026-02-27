package com.vanvan.repository.views;

import com.vanvan.model.views.FinancialReportView;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FinancialReportViewRepository extends JpaRepository<FinancialReportView, UUID> {}