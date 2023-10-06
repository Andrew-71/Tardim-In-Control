-- This is a very basic test script
-- This basically just verifies the mod works

local int = peripheral.find("digital_tardim_interface")
if int == nil then
    error("No interface found")
end

print(int.getOwnerName() .. "'s TARDIM")
print("FUEL: " .. int.getFuel() .. "/100")
print("IN FLIGHT? " .. int.isInFlight())
print("IS LOCKED? " .. int.isLocked())
