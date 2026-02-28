import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingsService, TravelResponseDTO, TravelPriceDTO } from '../../services/settings.service'; 

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html'
})
export class SettingsComponent implements OnInit {
  private settingsService = inject(SettingsService);

  searchQuery = '';
  
  // Variaveis para a criação de nova viagem (FALTAVAM AQUI)
  newRouteId: string = '';
  newVehicleId: string = '';
  currentRate = 0.75;
  newRateValue: number = 0;

  vehicles = signal<any[]>([]);
  routes = signal<any[]>([]);
  private _journeys = signal<TravelResponseDTO[]>([]);

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
      // Caso o Spring Boot devolva páginação, use data.content em vez de data
      next: (data: any) => {
        const viagens = data.content ? data.content : data;
        this._journeys.set(viagens);
      },
      error: (err) => console.error('Erro ao buscar viagens', err)
    });

    this.settingsService.getVehicles().subscribe({
      next: (data) => this.vehicles.set(data),
      error: (err) => console.error('Erro veículos', err)
    });

    this.settingsService.getRoutes().subscribe({
      next: (data) => this.routes.set(data),
      error: (err) => console.error('Erro rotas', err)
    });
  }

  // FUNÇÃO ADICIONAR QUE ESTAVA A FALTAR
  addJourney() {
    if(!this.newRouteId || !this.newVehicleId) {
      alert("Por favor, selecione uma rota e um veículo.");
      return;
    }

    const payload = {
      routeId: this.newRouteId,
      vehicleId: this.newVehicleId,
      status: "AGENDADA" // Status inicial padrão
    };

    this.settingsService.addJourney(payload).subscribe({
      next: (res: TravelResponseDTO) => {
        this._journeys.update(list => [...list, res]);
        alert("Viagem adicionada com sucesso!");
        this.newRouteId = '';
        this.newVehicleId = '';
      },
      error: (err) => console.error('Erro ao adicionar viagem', err)
    });
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