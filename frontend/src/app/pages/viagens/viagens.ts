import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Tag, TagVariant } from '../../components/tags/tags';
import { Buttons } from '../../components/buttons/buttons';

@Component({
  selector: 'app-viagens',
  standalone: true,
  imports: [CommonModule, Tag, Buttons],
  templateUrl: './viagens.html',
  styleUrls: ['./viagens.css']
})
export class Viagens {
  // Carousel state for scheduled trips
  scheduledScrollIndex = 0;

  // Cancel trip popup state
  showCancelPopup = false;
  cancelTripRef: any = null;

  // Ticket popup state
  showTicketPopup = false;
  ticketTripRef: any = null;
  ticketCode = 'ABC123';

  openTicketPopup(trip: any): void {
    this.ticketTripRef = trip;
    this.showTicketPopup = true;
  }

  closeTicketPopup(): void {
    this.showTicketPopup = false;
    this.ticketTripRef = null;
  }

  openCancelPopup(trip: any): void {
    this.cancelTripRef = trip;
    this.showCancelPopup = true;
  }

  closeCancelPopup(): void {
    this.showCancelPopup = false;
    this.cancelTripRef = null;
  }

  confirmCancelTrip(): void {
    // TODO: integrate with backend to actually cancel the trip
    console.log('Trip cancelled:', this.cancelTripRef);
    this.closeCancelPopup();
  }

  nextTrip = {
    month: 'FEV',
    day: '10',
    time: '08:00',
    origin: 'Garanhuns',
    destination: 'Recife',
    price: 'R$40,00',
    vehicle: 'Sprinter 2025 XXXX-XXX',
    pickupPoint: 'Rodoviária - Garanhuns',
    driverName: 'Nome do motorista',
    driverContact: 'Contato do motorista',
    driverRating: 4.8,
    variant: 'success' as TagVariant,
    statusLabel: 'Confirmado',
  };

  scheduledTrips = [
    {
      month: 'FEV', day: '10', origin: 'Garanhuns', destination: 'Recife',
      price: 'R$40,00', time: '08:00', vehicle: 'Sprinter 2025 XXXX-XXX',
      pickupPoint: 'Rodoviária - Garanhuns',
      driverName: 'Nome do motorista', driverContact: 'Contato do motorista',
      driverRating: 4.8,
      variant: 'warning' as TagVariant, statusLabel: 'Aguardando',
    },
    {
      month: 'FEV', day: '15', origin: 'Recife', destination: 'Garanhuns',
      price: 'R$40,00', time: '14:00', vehicle: 'Sprinter 2025 XXXX-XXX',
      pickupPoint: 'Rodoviária - Recife',
      driverName: 'Nome do motorista', driverContact: 'Contato do motorista',
      driverRating: 4.5,
      variant: 'success' as TagVariant, statusLabel: 'Confirmado',
    },
  ];

  pastTrips = [
    { month: 'FEV', day: '10', origin: 'Garanhuns', destination: 'Recife', price: 'R$40,00', variant: 'success' as TagVariant, statusLabel: 'Finalizado' },
    { month: 'FEV', day: '10', origin: 'Garanhuns', destination: 'Recife', price: 'R$40,00', variant: 'success' as TagVariant, statusLabel: 'Finalizado' },
    { month: 'FEV', day: '10', origin: 'Garanhuns', destination: 'Recife', price: 'R$40,00', variant: 'success' as TagVariant, statusLabel: 'Finalizado' },
  ];

  get currentScheduledTrip() {
    return this.scheduledTrips[this.scheduledScrollIndex] ?? this.scheduledTrips[0];
  }

  prevScheduled(): void {
    if (this.scheduledScrollIndex > 0) {
      this.scheduledScrollIndex--;
    }
  }

  nextScheduled(): void {
    if (this.scheduledScrollIndex < this.scheduledTrips.length - 1) {
      this.scheduledScrollIndex++;
    }
  }
}
