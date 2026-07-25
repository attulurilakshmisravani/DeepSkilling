// Hands-On 1: application bootstrap config (standalone API, replaces AppModule)
// Hands-On 8: HTTP client + interceptors registered here
// Hands-On 9: NgRx store + effects + devtools registered here
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';

import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth.interceptor';
import { errorHandlerInterceptor } from './interceptors/error-handler.interceptor';
import { loadingInterceptor } from './interceptors/loading.interceptor';

import { courseReducer } from './store/course/course.reducer';
import { CourseEffects } from './store/course/course.effects';
import { enrollmentReducer } from './store/enrollment/enrollment.reducer';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // Interceptors run in registration order for requests, reverse order for responses
    provideHttpClient(withInterceptors([authInterceptor, loadingInterceptor, errorHandlerInterceptor])),
    provideStore({ course: courseReducer, enrollment: enrollmentReducer }),
    provideEffects([CourseEffects]),
    provideStoreDevtools({ maxAge: 25 }),
  ],
};
