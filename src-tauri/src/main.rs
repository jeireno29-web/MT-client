mod accounts;
mod profiles;

use tauri::State;
use std::sync::Mutex;

struct AppState {
    profiles: Mutex<Vec<profiles::Profile>>,
}

#[tauri::command]
fn list_profiles(state: State<'_, AppState>) -> Vec<profiles::Profile> {
    state.profiles.lock().unwrap().clone()
}

#[tauri::command]
fn create_fabric_profile(
    name: String,
    minecraft_version: String,
    state: State<'_, AppState>
) -> Result<profiles::Profile, String> {
    let profile = profiles::Profile::fabric(&name, &minecraft_version);
    state.profiles.lock().unwrap().push(profile.clone());
    Ok(profile)
}

#[tauri::command]
fn create_offline_account(name: String) -> Result<accounts::Account, String> {
    if name.trim().is_empty() {
        return Err("Account name cannot be empty".into());
    }
    Ok(accounts::Account::offline(name.trim()))
}

fn main() {
    let profiles = vec![
        profiles::Profile::fabric("1.19.4 Fabric", "1.19.4"),
        profiles::Profile::fabric("1.20.1 Fabric", "1.20.1"),
        profiles::Profile::fabric("1.21.1 Fabric", "1.21.1"),
    ];

    tauri::Builder::default()
        .manage(AppState { profiles: Mutex::new(profiles) })
        .invoke_handler(tauri::generate_handler![
            list_profiles,
            create_fabric_profile,
            create_offline_account
        ])
        .run(tauri::generate_context!())
        .expect("error while running MT Client");
}
