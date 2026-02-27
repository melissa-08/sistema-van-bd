import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../services/client.service';

@Component({
  selector: 'app-cliente-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clients-edit.html',
})
export class ClienteEditComponent implements OnInit {

  @Input() cliente: any; // Recebe o cliente clicado na tabela
  @Output() aoFechar = new EventEmitter<boolean>();

  clienteEditado: any = {}; // Cópia local para edição
  mostrarSenha = false;
  carregando = false;

  constructor(private service: ClienteService) {}

  ngOnInit(): void {
    if (this.cliente) {
      this.clienteEditado = { ...this.cliente };
    }
  }

  fechar() {
    this.aoFechar.emit(false); // Emite 'false' indicando que apenas fechou/cancelou
  }

  salvar() {
    if (!this.clienteEditado.id) return;
    this.carregando = true;

    // Ajuste os nomes para bater com o que o Java espera
    const payload = {
      name: this.clienteEditado.nome || this.clienteEditado.name,
      email: this.clienteEditado.email,
      // ... outros campos
    };

    this.service.atualizar(this.clienteEditado.id, payload).subscribe({
      next: () => {
        this.carregando = false;
        this.aoFechar.emit(true);
      },
      error: () => {
        this.carregando = false;
        alert('Erro ao atualizar');
      }
    });
  }
}