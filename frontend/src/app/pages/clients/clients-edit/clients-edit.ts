import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../services/client.service'; 
import { ToastService } from '../../../components/toast/toast.service'; 
import { Toast } from '../../../components/toast/toast'; 

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
  carregando = signal(false);

  constructor(
    private clienteService: ClienteService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    if (this.cliente) {
      this.clienteEditado = { 
        id: this.cliente.id,
        name: this.cliente.name || this.cliente.nome || '',
        email: this.cliente.email || '',
        cpf: this.cliente.cpf || this.cliente.identidade || '',
        telephone: this.cliente.phone || this.cliente.telephone || this.cliente.telefone || '',
        birthDate: this.cliente.birthDate || this.cliente.dataNascimento || '',
        password: '' 
      };
    }
  }

  alternarVisualizacaoSenha() {
    this.mostrarSenha = !this.mostrarSenha;
  }

  // --- MÁSCARAS DE FORMATAÇÃO ---
  onCpfInput(event: any) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, ''); 
    if (value.length > 11) value = value.slice(0, 11);
    this.clienteEditado.cpf = value;
    input.value = value;
  }

  onPhoneInput(event: any) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    if (value.length > 11) value = value.slice(0, 11);

    if (value.length > 10) {
      value = value.replace(/^(\d\d)(\d{5})(\d{4}).*/, '($1)$2-$3');
    } else if (value.length > 6) {
      value = value.replace(/^(\d\d)(\d{4})(\d{0,4}).*/, '($1)$2-$3');
    } else if (value.length > 2) {
      value = value.replace(/^(\d\d)(\d{0,5}).*/, '($1)$2');
    } else if (value.length > 0) {
      value = value.replace(/^(\d*)/, '($1');
    }
    this.clienteEditado.telephone = value;
    input.value = value;
  }

  onBirthdateInput(event: any) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    if (value.length > 8) value = value.slice(0, 8);

    if (value.length > 4) {
      value = value.replace(/^(\d{2})(\d{2})(\d{0,4}).*/, '$1/$2/$3');
    } else if (value.length > 2) {
      value = value.replace(/^(\d{2})(\d{0,2}).*/, '$1/$2');
    }
    this.clienteEditado.birthDate = value;
    input.value = value;
  }

  fechar() {
    this.aoFechar.emit(false); 
  }

  salvar() {
    if (!this.clienteEditado.id) return;

    const d = this.clienteEditado;

    // Validações básicas
    if (!d.name || !d.email || !d.cpf || !d.telephone) {
      this.toastService.error('Preencha todos os campos obrigatórios.');
      return;
    }

    if (d.cpf.length !== 11) {
      this.toastService.error('O CPF deve ter exatamente 11 dígitos.');
      return;
    }

    this.carregando.set(true);

    // Monta o objeto para o Backend
    const payload: any = {
      name: d.name,
      email: d.email,
      cpf: d.cpf,
      telephone: d.telephone,
      birthDate: d.birthDate,
      role: 'passenger'
    };

    // Só envia a senha se ele digitou uma nova
    if (d.password && d.password.trim() !== '') {
      payload.password = d.password;
    }

    this.clienteService.editar(this.clienteEditado.id, payload).subscribe({
      next: () => {
        this.carregando.set(false);
        this.toastService.success('Cliente atualizado com sucesso!');
        this.aoFechar.emit(true); // Emite 'true' para atualizar a tabela na tela principal
      },
      error: (err: any) => {
        this.carregando.set(false);
        if (err?.error?.message) {
          this.toastService.error(err.error.message);
        } else {
          this.toastService.error('Erro ao editar cliente. Verifique os dados.');
        }
      }
    });
  }
}