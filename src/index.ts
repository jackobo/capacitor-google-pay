import { registerPlugin } from '@capacitor/core';

import type { CapacitorGooglePayPlugin } from './definitions';

const CapacitorGooglePay = registerPlugin<CapacitorGooglePayPlugin>('CapacitorGooglePay', {
  web: () => import('./web').then((m) => new m.CapacitorGooglePayWeb()),
});

export * from './definitions';
export { CapacitorGooglePay };
