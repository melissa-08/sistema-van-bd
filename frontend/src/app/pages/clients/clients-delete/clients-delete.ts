import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
// 1. Certifique-se de que o import está correto
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

  // 2. Você precisa injetar o serviço aqui no constructor
  constructor(private service: ClienteService) {} 

  fechar() {
    this.aoFechar.emit(false);
  }

  excluir() {
    if (!this.cliente || !this.cliente.id) return;

    this.carregando = true;

    // 3. Agora o 'this.service' vai funcionar
    this.service.excluir(this.cliente.id).subscribe({
      next: () => {
        this.carregando = false;
        this.aoFechar.emit(true); // Avisa o componente pai para recarregar a lista
      },
      error: (err) => {
        this.carregando = false;
        console.error('Erro ao excluir:', err);
        alert('Erro ao excluir cliente.');
      }
    });
  }
}