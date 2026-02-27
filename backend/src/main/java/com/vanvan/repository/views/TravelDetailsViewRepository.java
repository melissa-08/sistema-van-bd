package com.vanvan.repository.views;

import com.vanvan.model.views.TravelDetailsView;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TravelDetailsViewRepository extends JpaRepository<TravelDetailsView, UUID> {}