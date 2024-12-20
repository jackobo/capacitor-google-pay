export interface CapacitorGooglePayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
