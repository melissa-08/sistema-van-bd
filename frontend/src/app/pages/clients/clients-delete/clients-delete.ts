import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../services/client.service'; 

@Component({
  selector: 'app-cliente-delete',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './clients-delete.html',
})
export class ClienteDeleteComponent {

  @Input() cliente: any;
  @Output() aoFechar = new EventEmitter<boolean>();

  carregando = false;

  constructor(private service: ClienteService) {}

  fechar() {
    this.aoFechar.emit(false);
  }

  excluir() {
    if (!this.cliente || !this.cliente.id) return;

    this.carregando = true;
    console.log('Excluindo ID:', this.cliente.id);

    this.service.excluir(this.cliente.id).subscribe({
      next: () => {
        this.carregando = false;
        this.aoFechar.emit(true); // Fecha o modal e recarrega a lista
      },
      error: (err: any) => {
        this.carregando = false;
        console.error('Erro ao excluir:', err);
        alert('Erro ao excluir. Verifique a aba Network (F12).');
      }
    });
  }
}