import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Buttons } from '../../components/buttons/buttons';

@Component({
  selector: 'app-motorista-page',
  standalone: true,
  imports: [CommonModule, Buttons],
  templateUrl: './motorista-page.html',
  styleUrls: ['./motorista-page.css']
})
export class MotoristaPage {
  // ===== Current Trip =====
  currentTrip = {
    availableSeats: 14,
    origin: 'Garanhuns',
    destination: 'Recife',
    distance: '230km',
    departureLocation: 'Rodoviária - Garanhuns',
    arrivalLocation: 'Rodoviária - Recife',
    date: '10 Fev',
    time: '08:00',
  };

  // Seat management
  adjustSeats(delta: number): void {
    const newVal = this.currentTrip.availableSeats + delta;
    if (newVal >= 0) {
      this.currentTrip.availableSeats = newVal;
    }
  }

  saveSeats(): void {
    console.log('Seats saved:', this.currentTrip.availableSeats);
  }

  cancelCurrentTrip(): void {
    console.log('Current trip cancelled');
  }

  startTrip(): void {
    console.log('Trip started');
  }

  // ===== Past Trips =====
  pastTrips = [
    {
      origin: 'Garanhuns',
      destination: 'Recife',
      price: 'R$400,00',
      distance: '60km',
      date: '10 Fev',
      time: '08:00',
    },
  ];

  // ===== Report Data =====
  report = {
    days: 7,
    tripsCompleted: 16,
    months: 1,
    profit: 'R$2300,00',
    dailyProfits: [42, 73, 59, 51, 67, 100], // bar heights as percentage of max
  };

  // ===== Pricing =====
  pricing = {
    ratePerKm: 'R$0,70/km',
    routes: [
      { distance: '50km', price: 'R$35,00' },
      { distance: '100km', price: 'R$70,00' },
    ],
  };

  // ===== Popup State =====
  showEditVehiclePopup = false;

  // ===== Navigation =====
  ofertarViagem(): void {
    console.log('Navigate to ofertar viagem');
  }

  verMaisViagens(): void {
    console.log('Navigate to ver mais viagens');
  }

  alterarValores(): void {
    console.log('Navigate to alterar valores');
  }

  editarVeiculo(): void {
    this.showEditVehiclePopup = true;
  }

  closeEditVehiclePopup(): void {
    this.showEditVehiclePopup = false;
  }
}
