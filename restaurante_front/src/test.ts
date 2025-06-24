import 'zone.js/testing';
import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

declare const require: {
  context(path: string, deep?: boolean, filter?: RegExp): {
    keys(): string[];
    <T>(id: string): T;
  };
};

// Inicializa el entorno de pruebas
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting(),
);

// 👉 ESTA ES LA PARTE MÁS IMPORTANTE:
// Busca todos los archivos que terminen en .spec.ts
const context = require.context('./', true, /\.spec\.ts$/);
context.keys().forEach(context);
