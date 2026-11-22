#[derive(Clone, Debug, serde::Serialize, serde::Deserialize)]
pub struct Account {
    pub name: String,
    pub mode: AccountMode,
    pub uuid: String,
}

#[derive(Clone, Debug, serde::Serialize, serde::Deserialize)]
#[serde(rename_all = "lowercase")]
pub enum AccountMode {
    Microsoft,
    Offline,
}

impl Account {
    pub fn offline(name: &str) -> Self {
        let uuid = format!("{:#018x}", blake3::hash(name.as_bytes()).as_bytes()[0..8].iter().fold(0u128, |acc, byte| (acc << 8) | *byte as u128));
        Self { name: name.to_string(), mode: AccountMode::Offline, uuid }
    }
}
