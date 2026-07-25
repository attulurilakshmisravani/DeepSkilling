// Hands-On 8, Task 3, Step 88: adds a mock bearer token to every outgoing request
import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authReq = req.clone({
    setHeaders: { Authorization: 'Bearer mock-token-12345' },
  });
  return next(authReq);
};
