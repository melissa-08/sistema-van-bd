import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingsService, Journey } from '../../services/settings.service'; 

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html'
})
export class SettingsComponent implements OnInit {
  // Injeta o serviço de comunicação com o back-end
  private settingsService = inject(SettingsService);

  searchQuery = '';
  newOrigin = '';
  newDestination = '';
  newRateValue: number = 0;

  currentRate = 0.75;
  distance = 63; 

  // Adicione isso junto com as outras variáveis da sua classe SettingsComponent

  // Lista de veículos (No futuro, você fará um GET no back-end para preencher isso)
  vehicles = signal([
    { id: '123e4567-e89b-12d3-a456-426614174001', name: 'Van 01 (Placa ABC-1234) - Motorista: João' },
    { id: '123e4567-e89b-12d3-a456-426614174002', name: 'Micro-ônibus (Placa XYZ-9876) - Motorista: Maria' }
  ]);

  // Lista de rotas (No futuro, também virá do back-end)
  routes = signal([
    { id: '987fcdeb-51a2-43d7-9012-3456789abcde', name: 'Garanhuns -> Recife (Rota Principal)' },
    { id: '987fcdeb-51a2-43d7-9012-3456789abcdf', name: 'Caruaru -> Maceió (Rota Litoral)' }
  ]);

  // Inicia vazio, pois os dados virão do back-end
  private _journeys = signal<Journey[]>([]);

  journeys = computed(() => {
    const query = this.searchQuery.toLowerCase();
    return this._journeys().filter(j => 
      j.name.toLowerCase().includes(query) || 
      j.origin.toLowerCase().includes(query) || 
      j.destination.toLowerCase().includes(query)
    );
  });

  get estimatedValue(): number {
    return this.distance * this.currentRate;
  }

  // Variáveis dos Modais
  showEditModal = false;
  showDeleteModal = false;
  selectedJourney: any = null;

  // Ao iniciar o componente, busca os dados da API
  ngOnInit() {
    this.loadData();
  }

  // --- MÉTODOS DE INTEGRAÇÃO COM A API ---

  loadData() {
    // Busca as rotas
    this.settingsService.getJourneys().subscribe({
      next: (data) => this._journeys.set(data),
      error: (err) => console.error('Erro ao buscar viagens', err)
    });

    // Opcional: Busca o valor da tarifa atual do back-end
    // this.settingsService.getRate().subscribe(rate => this.currentRate = rate.value);
  }

  addJourney() {
    if (!this.newOrigin || !this.newDestination) {
      alert('Preencha origem e destino!');
      return;
    }

    const newJourney: Journey = {
      name: `${this.newOrigin}-${this.newDestination}`,
      origin: this.newOrigin,
      destination: this.newDestination
    };

    // Envia o POST para a API
    this.settingsService.addJourney(newJourney).subscribe({
      next: (savedJourney) => {
        // Atualiza a tela com o dado que retornou do banco (que agora vem com um ID real)
        this._journeys.update(list => [...list, savedJourney]);
        this.newOrigin = '';
        this.newDestination = '';
      },
      error: (err) => {
        console.error('Erro ao salvar', err);
        alert('Erro ao conectar com o servidor.');
      }
    });
  }

  confirmDelete() {
    // Envia o DELETE para a API
    this.settingsService.deleteJourney(this.selectedJourney.id).subscribe({
      next: () => {
        // Remove da tela apenas se o back-end confirmou a exclusão
        this._journeys.update(list => list.filter(j => j.id !== this.selectedJourney.id));
        this.closeModals();
      },
      error: (err) => console.error('Erro ao excluir', err)
    });
  }

  confirmSaveEdit() {
    // Envia o PUT para a API
    this.settingsService.updateJourney(this.selectedJourney.id, this.selectedJourney).subscribe({
      next: (updatedJourney) => {
        // Atualiza o item na tela
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
      // Lógica local temporária sem API para a tarifa ainda:
      this.currentRate = this.newRateValue;
      alert(`Nova tarifa de R$${this.currentRate.toFixed(2)} salva com sucesso!`);
      this.newRateValue = 0;
    } else {
      alert('Insira um valor válido para a tarifa.');
    }
  }

  openEditModal(journey: any) {
    this.selectedJourney = { ...journey };
    this.showEditModal = true;
  }

  openDeleteModal(journey: any) {
    this.selectedJourney = journey;
    this.showDeleteModal = true;
  }

  closeModals() {
    this.showEditModal = false;
    this.showDeleteModal = false;
    this.selectedJourney = null;
  }
}