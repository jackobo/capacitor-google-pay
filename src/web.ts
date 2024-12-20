import { WebPlugin } from '@capacitor/core';

import type { CapacitorGooglePayPlugin } from './definitions';

export class CapacitorGooglePayWeb extends WebPlugin implements CapacitorGooglePayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
