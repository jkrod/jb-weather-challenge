import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { GooglePlaceDirective } from 'ngx-google-places-autocomplete';
import { Address } from 'ngx-google-places-autocomplete/objects/address';
import { Observable } from 'rxjs';

interface WeatherResponse {
  precipitation: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  response$: Observable<WeatherResponse>;
  formattedAddress = '';
  longitude = 0;
  latitude = 0;

  options = {
    componentRestrictions: {
      country: ['DK']
    }
  }

  constructor(private http: HttpClient) {}

  @ViewChild("placesRef", {static : false} ) placesRef : GooglePlaceDirective;
  public handleAddressChange(address: Address) {
    this.formattedAddress = address.formatted_address;
    this.longitude = address.geometry.location.lng();
    this.latitude = address.geometry.location.lat();
    this.getPrecipitation();
 }

  ngOnInit(): void {
  }

  private getPrecipitation() {
    this.response$ = this.http.get<WeatherResponse>('/api/' + this.longitude.toString() + '/' + this.latitude.toString());
  }
}
