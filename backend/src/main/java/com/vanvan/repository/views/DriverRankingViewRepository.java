package com.vanvan.repository.views;

import com.vanvan.model.views.DriverRankingView;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DriverRankingViewRepository extends JpaRepository<DriverRankingView, UUID> {}
