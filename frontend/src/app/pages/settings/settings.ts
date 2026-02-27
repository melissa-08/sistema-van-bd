import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingsService } from '../../services/settings.service'; 

export interface TravelPriceDTO {
  boardingStopId: string;
  dropOffStopId: string;
  price: number;
}

export interface TravelResponseDTO {
  id: string;
  departureTime: string; 
  status: string;
  driverName: string;
  vehiclePlate: string;
  routeName: string;
  prices: TravelPriceDTO[]; 
}

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html'
})
export class SettingsComponent implements OnInit {
  private settingsService = inject(SettingsService);

  searchQuery = '';
  
  // Variáveis do modal (mantidas se for reutilizar para adicionar tarifas)
  newRateValue: number = 0;
  currentRate = 0.75;

  vehicles = signal<any[]>([]);
  routes = signal<any[]>([]);

  // 2. LISTA DE VIAGENS AGORA USA O NOVO DTO
  private _journeys = signal<TravelResponseDTO[]>([]);

  // 3. FILTRO ATUALIZADO (pesquisa por rota, motorista ou placa)
  filteredJourneys = computed(() => {
    const query = this.searchQuery.toLowerCase();
    return this._journeys().filter(j => 
      (j.routeName && j.routeName.toLowerCase().includes(query)) ||
      (j.driverName && j.driverName.toLowerCase().includes(query)) ||
      (j.vehiclePlate && j.vehiclePlate.toLowerCase().includes(query)) ||
      (j.status && j.status.toLowerCase().includes(query))
    );
  });

  showEditModal = false;
  showDeleteModal = false;
  selectedJourney: any = null;

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.settingsService.getJourneys().subscribe({
      next: (data: TravelResponseDTO[]) => this._journeys.set(data),
      error: (err) => console.error('Erro ao buscar viagens', err)
    });

    if (this.settingsService.getVehicles) {
      this.settingsService.getVehicles().subscribe({
        next: (data) => this.vehicles.set(data),
        error: (err) => console.error('Erro veículos', err)
      });
    }

    if (this.settingsService.getRoutes) {
      this.settingsService.getRoutes().subscribe({
        next: (data) => this.routes.set(data),
        error: (err) => console.error('Erro rotas', err)
      });
    }
  }

  deleteJourney() {
    this.settingsService.deleteJourney(this.selectedJourney.id).subscribe({
      next: () => {
        this._journeys.update(list => list.filter(j => j.id !== this.selectedJourney.id));
        this.closeModals();
      },
      error: (err) => console.error('Erro ao excluir', err)
    });
  }

  confirmSaveEdit() {
    this.settingsService.updateJourney(this.selectedJourney.id, this.selectedJourney).subscribe({
      next: (updatedJourney: TravelResponseDTO) => {
        this._journeys.update(list => 
          list.map(j => j.id === updatedJourney.id ? updatedJourney : j)
        );
        this.closeModals();
      },
      error: (err) => console.error('Erro ao editar', err)
    });
  }

  saveRate() {
    if (this.newRateValue > 0) {
      this.currentRate = this.newRateValue;
      alert(`Nova tarifa de R$${this.currentRate.toFixed(2)} salva com sucesso!`);
      this.newRateValue = 0;
    } else {
      alert('Insira um valor válido para a tarifa.');
    }
  }

  openEditModal(journey: TravelResponseDTO) {
    this.selectedJourney = { ...journey };
    this.showEditModal = true;
  }

  openDeleteModal(journey: TravelResponseDTO) {
    this.selectedJourney = journey;
    this.showDeleteModal = true;
  }

  closeModals() {
    this.showEditModal = false;
    this.showDeleteModal = false;
    this.selectedJourney = null;
  }
}