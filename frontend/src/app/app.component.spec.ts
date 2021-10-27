import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let httpClient: HttpClient = jasmine.createSpyObj('HttpClient', ['get']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      providers: [
        {
          provide: HttpClient,
          useValue: httpClient,
        },
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render greeting', () => {
    // Arrange
    const response = { fact: 'A man once ate a bicycle' };
    (httpClient.get as jasmine.Spy).and.returnValue(of(response));

    const fixture = TestBed.createComponent(AppComponent);

    fixture.detectChanges();
    const compiled = fixture.nativeElement;

    // Assert
    expect(compiled.querySelector('.fact').textContent).toContain(
      response.fact
    );
  });
});
