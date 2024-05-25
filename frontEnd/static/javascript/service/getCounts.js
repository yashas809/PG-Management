export function getCountofUsers(apiResponses) {
    // Count of role names
    const roleCountArray = [];

    // Create a Map to store role counts temporarily
    const roleCountMap = new Map();

    apiResponses.forEach(entry => {
        const roleName = entry.roleName;
        if (roleName) {
            const count = roleCountMap.get(roleName) || 0;
            roleCountMap.set(roleName, count + 1);
        }
    });

    // Convert Map entries to array of objects
    for (const [roleName, count] of roleCountMap.entries()) {
        roleCountArray.push({ roleName, count });
    }

    console.log("Role Name Counts:");
    roleCountArray.forEach(entry => {
        console.log(`${entry.roleName}: ${entry.count}`);
    });

    return roleCountArray;
}
