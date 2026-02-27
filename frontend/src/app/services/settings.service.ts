import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Journey {
  id?: number; 
  name: string;
  origin: string;
  destination: string;
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private http = inject(HttpClient);
  
  // Mude para a URL real do seu back-end Spring Boot/Node
  private apiUrl = 'http://localhost:8080/api/journeys'; 
  private rateApiUrl = 'http://localhost:8080/api/rates';

  // Buscar todas as viagens (GET)
  getJourneys(): Observable<Journey[]> {
    return this.http.get<Journey[]>(this.apiUrl);
  }

  // Criar nova viagem (POST)
  addJourney(journey: Journey): Observable<Journey> {
    return this.http.post<Journey>(this.apiUrl, journey);
  }

  // Atualizar viagem existente (PUT)
  updateJourney(id: number, journey: Journey): Observable<Journey> {
    return this.http.put<Journey>(`${this.apiUrl}/${id}`, journey);
  }

  // Deletar viagem (DELETE)
  deleteJourney(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // ---- Exemplo para a tarifa ----
  getRate(): Observable<{ value: number }> {
    return this.http.get<{ value: number }>(this.rateApiUrl);
  }

  updateRate(value: number): Observable<any> {
    return this.http.put(this.rateApiUrl, { value });
  }
}