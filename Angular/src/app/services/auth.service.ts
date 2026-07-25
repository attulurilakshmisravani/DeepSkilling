// Hands-On 7, Task 2: backing service for the auth guard (hardcoded for the exercise)
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  isLoggedIn = true;
}
