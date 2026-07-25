# Student Course Portal — Digital Nurture 5.0 (Angular v20)

One cumulative Angular project covering all 10 hands-on exercises. Each hands-on's
work lives in the files below rather than in separate projects, per the exercise book's
submission guidelines.

## Setup

```bash
npm install
npm start              # ng serve -> http://localhost:4200
npx json-server --watch db.json --port 3000   # mock REST API for Hands-On 8+
npm test                # ng test (Karma/Jasmine, Hands-On 10)
```

## Where each hands-on lives

| Hands-On | Topic | Key files |
|---|---|---|
| 1 | Setup, structure, first component | `notes.txt`, `angular.json`, `src/app/app.component.*`, `components/header/*`, `pages/home/*` |
| 2 | Binding, lifecycle hooks, @Input/@Output | `pages/home/home.component.ts`, `components/course-card/course-card.component.ts` |
| 3 | Directives & pipes | `directives/highlight.directive.ts`, `pipes/credit-label.pipe.ts`, `pages/course-list/*`, `components/course-card/*` |
| 4 | Template-driven forms | `pages/enrollment-form/*` |
| 5 | Reactive forms, FormArray, custom/async validators | `pages/reactive-enrollment-form/*` |
| 6 | Services & DI | `services/course.service.ts`, `services/enrollment.service.ts`, `components/notification/*` |
| 7 | Routing, guards, lazy loading | `app.routes.ts`, `pages/enrollment-form/enrollment.routes.ts`, `guards/*`, `pages/course-detail/*` |
| 8 | HttpClient, RxJS, interceptors | `services/course.service.ts`, `interceptors/*`, `services/loading.service.ts` |
| 9 | NgRx store | `store/course/*`, `store/enrollment/*`, `pages/course-list/course-list.component.ts` |
| 10 | Unit testing | `components/course-card/course-card.component.spec.ts`, `services/course.service.spec.ts`, `pages/course-list/course-list.component.spec.ts` |

## Notes

- Built with Angular 20 standalone APIs (no NgModules) — `app.config.ts` replaces `AppModule`.
- `db.json` is the mock dataset for `json-server`, used by Hands-On 8 onward.
- Run `ng generate ...` commands yourself if you'd like to see the CLI scaffold each file from
  scratch; the generated files here already contain the completed hands-on code so you can also
  just read/run them directly.
- `node_modules/` is intentionally excluded — run `npm install` after unzipping.
